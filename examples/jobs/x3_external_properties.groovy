String repositoryBasePath = '/home/kamils/confitura/projects'

def parseMetadata(File project) {
    File metadata = new File(project, 'metadata.json')
    return new groovy.json.JsonSlurper().parseText(metadata.text)
}

new File(repositoryBasePath).eachDir() { project ->
    def config = parseMetadata(project)

    job("${project.name}") {
        scm {
            git "file://${repositoryBasePath}/${project.name}"
        }
        triggers {
            cron(config.trigger)
        }
        steps {
            shell "echo \"Hello ${config.greet}!\""
        }
    }
}