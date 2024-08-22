pipeline {
    agent any

    tools 
    {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN_HOME"
    }

    stages 
    {
      stage('Git') 
        {
           steps 
             {
                // Get some code from a GitHub repository
                git branch: 'main', credentialsId: '1b0433ad-4f55-4ecb-847d-4822c46d6a05', url: 'https://github.com/pranavkumar2001/AutomationProject'
             }
        }
      stage('Build') 
      {
          steps 
          {
                // Run Maven on a Unix agent.
                bat "mvn clean test"
          }
      }
    }
      post 
      {
          success 
          {
            // Executed regardless of the build status.
            echo "First Pipeline created successfully"
            
            emailext attachLog: true, body: '''Hi There,

PFA the build log for pipeline creation.''', compressLog: true, subject: 'Pipeline build successfully', to: 'kumarpranav2001@gmail.com'
          }
      }
}
