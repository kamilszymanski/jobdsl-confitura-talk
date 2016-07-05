package pl.confitura.examples.jobs

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.MemoryJobManagement
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit
import org.junit.ComparisonFailure
import spock.lang.Specification

class ConfigComparingSpec extends Specification {

    MemoryJobManagement jobManagement = new MemoryJobManagement()
    DslScriptLoader scriptLoader = new DslScriptLoader(jobManagement)

    def 'generated configuration should match the expected one'() {
        given:
            String script = """
                                job('hello') {
                                    steps {
                                        shell 'echo Hello'
                                    }
                                }
                            """
            String expectedConfig = """
                                        <project>
                                            <actions></actions>
                                            <description></description>
                                            <keepDependencies>false</keepDependencies>
                                            <properties></properties>
                                            <scm class='hudson.scm.NullSCM'></scm>
                                            <canRoam>true</canRoam>
                                            <disabled>false</disabled>
                                            <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
                                            <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
                                            <triggers class='vector'></triggers>
                                            <concurrentBuild>false</concurrentBuild>
                                            <builders>
                                                <hudson.tasks.Shell>
                                                    <command>echo Hello</command>
                                                </hudson.tasks.Shell>
                                            </builders>
                                            <publishers></publishers>
                                            <buildWrappers></buildWrappers>
                                        </project>
                                    """

        when:
            scriptLoader.runScript(script)

        then:
            generatedConfigMatches(expectedConfig)
    }

    void generatedConfigMatches(String expected) {
        String actual = jobManagement.savedConfigs.hello
        Diff diff = compareXml(expected, actual)
        if (!diff.identical()) {
            throw new ComparisonFailure("Generated configuration does not match expected one", expected, actual)
        }
    }

    Diff compareXml(String expected, String actual) {
        XMLUnit xmlUnit = new XMLUnit()
        xmlUnit.setIgnoreWhitespace(true)
        xmlUnit.setNormalizeWhitespace(true)
        return xmlUnit.compareXML(expected, actual)
    }
}
