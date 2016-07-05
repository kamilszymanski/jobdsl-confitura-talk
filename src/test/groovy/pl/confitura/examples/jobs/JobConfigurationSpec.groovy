package pl.confitura.examples.jobs

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Specification

class JobConfigurationSpec extends Specification {

    MemoryJobManagement jobManagement = new MemoryJobManagement()
    DslScriptLoader scriptLoader = new DslScriptLoader(jobManagement)

    def 'should execute shell command'() {
        given:
            String script = """
                                job('hello') {
                                    steps {
                                        shell 'echo Hello'
                                    }
                                }
                            """

        when:
            scriptLoader.runScript(script)

        then:
            shellCommandToBeExecuted == 'echo Hello'
    }

    String getShellCommandToBeExecuted() {
        String xml = jobManagement.savedConfigs.hello
        def jobConfig = new XmlSlurper().parseText(xml)
        return jobConfig.builders.'hudson.tasks.Shell'.command
    }
}
