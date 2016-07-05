package pl.confitura.jobdsl.pipeline.customizable.step

import javaposse.jobdsl.dsl.DslFactory
import pl.confitura.jobdsl.pipeline.customizable.PipelineProperties

abstract class PipelineStep {

    final String jobName

    protected DslFactory dslFactory
    protected PipelineProperties context
    protected String downstreamJob

    protected PipelineStep(String jobName) {
        this.jobName = jobName
    }

    abstract void build()

}
