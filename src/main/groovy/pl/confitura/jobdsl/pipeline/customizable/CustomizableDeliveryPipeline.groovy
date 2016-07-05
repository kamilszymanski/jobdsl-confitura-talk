package pl.confitura.jobdsl.pipeline.customizable

import groovy.transform.InheritConstructors
import pl.confitura.jobdsl.pipeline.customizable.step.PipelineStep

import static pl.confitura.jobdsl.pipeline.customizable.view.ViewFactory.createDeliveryPipelineView

@InheritConstructors
class CustomizableDeliveryPipeline extends PipelineAssembler {

    void build(List<PipelineStep> pipelineSteps) {
        List<String> jobs = assemble(pipelineSteps)
        createDeliveryPipelineView(dslFactory, projectName, jobs[0])
    }

}
