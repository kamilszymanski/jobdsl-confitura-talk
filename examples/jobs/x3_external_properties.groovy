/**
 * To run this example you have to change `repositoryBasePath` to a directory containing git projects
 * that have metadata.json file in their root directories.
 *
 * metadata.json files should define `great` and `trigger` properties, e.g.:
 *  {
 *    "greet": "Confitura",
 *    "trigger": "0 9 2 7 0"
 *  }
 */

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