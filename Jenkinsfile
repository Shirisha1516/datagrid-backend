def mvnCmd = "mvn -s configuration/settings.xml"
def templatePath= "cicd/template.json"
def sonarHostURL = "https://sonarqube-cicd.api.tatamotors.com"
def proxy= "http://172.18.88.11:80"
pipeline {
	    environment { 
		    NAME='aip-crm-position-jdg'
		    DEV_NAMESPACE='dev-esb'
		    QA_NAMESPACE='qa-esb'
		    PROD_NAMESPACE='prod-esb'
    	    }
            agent {
		label 'maven'
            }
		
            stages {
              stage('Checkout') {
		      when {
			      expression {
				      env.PROJECT == env.DEV_NAMESPACE
			      }     
			}
                steps {
			withEnv(['HTTPS_PROXY=${proxy}']){
                 	  checkout scm
			}
		    }
                }
              stage('Build App') { 
		       when {
			      expression {
				      env.PROJECT == env.DEV_NAMESPACE
			      } 
			}
                steps {
                  sh "${mvnCmd} clean package -DskipTests=true -Dversion=${env.BUILD_NUMBER}"
                }
              }
	       stage('Execute Unit Test Cases') {
		        when {
			      expression {
				      env.PROJECT == env.DEV_NAMESPACE
			      } 
			}
		  steps {     
                 sh "${mvnCmd} test -Dversion=${env.BUILD_NUMBER}"
		  }	  
              }	    
		stage('Code Analysis') {
			 when {
			       expression {
				     env.PROJECT == env.DEV_NAMESPACE
			      } 
			}
                steps {
                  script {
			  sh "${mvnCmd} sonar:sonar -Dsonar.host.url=${sonarHostURL} -Dversion=${env.BUILD_NUMBER}"
                  }
                }
              } 
	      stage('Archive App') {
		       when {
			      expression {
				     env.PROJECT == env.DEV_NAMESPACE
			      } 
			}
                steps {
                  sh "${mvnCmd} deploy -DskipTests=true -Dversion=${env.BUILD_NUMBER}"
                }
              } 
	      stage ('Deploy Template') {
		      steps{
			     script{
				openshift.withCluster() {
					openshift.withProject(env.PROJECT) {
						echo "Using project: ${openshift.project()}"
						if(!openshift.selector("all", [ template : "${NAME}"]).exists()){
							openshift.newApp(templatePath,"-p PROJECT=${env.PROJECT}")
						} 
						if (env.PROJECT == "${QA_NAMESPACE}"){
							openshift.tag("${DEV_NAMESPACE}/${NAME}:latest", "${PROJECT}/${NAME}:latest")	
						}
						if (env.PROJECT == "${PROD_NAMESPACE}"){
							openshift.tag("${QA_NAMESPACE}/${NAME}:latest", "${PROJECT}/${NAME}:latest")	
						}
					} //Project
				  } //Cluster
			    } //script    
		   } //steps
	       }    //stage
	       stage('Image build') { 
		        when {
			       expression {
				      env.PROJECT == env.DEV_NAMESPACE
			      } 
			}
		  steps{      
			  script{
			    try { 	  
	               	      timeout(time: 120, unit: 'SECONDS') {	  
				openshift.withCluster() {
					openshift.withProject(env.PROJECT) {
						echo "Using project: ${openshift.project()}"
						def build = openshift.selector("bc", "${NAME}").startBuild("--from-file=target/${NAME}-${env.BUILD_NUMBER}.jar", "--wait=true")
						build.untilEach {
							return it.object().status.phase == "Complete"
						} //build
				     } //project
			         } //cluster
		             }	//timeout		  
                          } //try
			   catch ( e ) {
        		   	println "Build not successful. Check logs."
        		   	currentBuild.result = 'FAILURE'	
    		       } //catch
		     } //script
		  }  //steps
	       }  //stage     
		 stage('Deployment') {     	 
		     steps{ 
			  script{     
			     try {  
				timeout(time: 5, unit: 'MINUTES') {  
				  openshift.withCluster() {
					openshift.withProject(env.PROJECT) {
					 echo "Using project: ${openshift.project()}"
						if (openshift.selector("cm", "${NAME}").exists()){
						    openshift.selector("cm", "${NAME}").delete();
						}
						 echo "Using project: ${openshift.project()}"
						openshift.create("cm","${NAME}","--from-file=src/main/resources/application-${PROJECT}.properties")
					    echo "Using project: ${openshift.project()}"
					   def deploy= openshift.selector("dc", "${NAME}")
					   echo "Using project: ${openshift.project()}"
				           deploy.rollout().latest();
					   } //project
				      }   //cluster   
		                } //timeout
			     } //try 
				  catch ( e ) {
					println "Deployment not successful. Check logs."
					currentBuild.result = 'FAILURE'	  
    		             } //catch 
			  } //script	  
		      }	//steps
		 } //stage
		stage('Image Tag') {
	           steps {
			script {
		 	      openshift.withCluster() {
				      openshift.tag("${PROJECT}/${NAME}:latest", "${PROJECT}/${NAME}:${env.BUILD_NUMBER}")
		  	     }
			}
	      	    }
	    	 }     
            } //stages
       } //pipeline
