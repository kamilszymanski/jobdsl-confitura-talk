package pl.confitura.examples.jobs

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.GeneratedItems
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Specification

class GeneratedJobsSpec extends Specification {

    MemoryJobManagement jobManagement = new MemoryJobManagement()
    DslScriptLoader scriptLoader = new DslScriptLoader(jobManagement)

    def 'should generate all defined jobs'() {
        given:
            String script = """
                                job('hello') {
                                    steps {
                                        shell 'echo Hello'
                                    }
                                }

                                job('bye') {
                                    steps {
                                        shell 'echo Bye'
                                    }
                                }
                            """
            Set expectedJobNames = ['hello', 'bye']

        when:
            GeneratedItems generatedItems = scriptLoader.runScript(script)

        then:
            jobNamesOf(generatedItems) == expectedJobNames
    }

    Set jobNamesOf(GeneratedItems generatedItems) {
        return generatedItems.jobs*.jobName
    }

}
