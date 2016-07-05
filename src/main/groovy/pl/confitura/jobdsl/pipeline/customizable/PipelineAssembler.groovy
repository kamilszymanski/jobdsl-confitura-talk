package pl.confitura.jobdsl.pipeline.customizable

import javaposse.jobdsl.dsl.DslFactory
import pl.confitura.jobdsl.pipeline.customizable.step.PipelineStep

class PipelineAssembler {

    protected final DslFactory dslFactory
    protected final String projectName
    protected final PipelineProperties context

    protected PipelineAssembler(DslFactory factory, String project, Map parameters) {
        dslFactory = factory
        projectName = project
        context = new PipelineProperties(parameters)
    }

    protected List<String> assemble(List<Class<? extends PipelineStep>> pipelineSteps) {
        List jobs = pipelineSteps.reverse().inject([]) { acc, step ->
            String downstreamJob = acc.empty ? null : acc.last().jobName
            acc << createJob(step, downstreamJob)
        }
        jobs.each {
            it.build()
        }
        return jobs.reverse()*.jobName
    }

    protected PipelineStep createJob(Class<? extends PipelineStep> step, String downstreamJob) {
        PipelineStep job = step.newInstance([projectName].toArray())
        job.dslFactory = dslFactory
        job.context = context
        job.downstreamJob = downstreamJob
        return job
    }

}
