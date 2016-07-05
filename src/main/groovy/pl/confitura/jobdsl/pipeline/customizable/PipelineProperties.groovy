package pl.confitura.jobdsl.pipeline.customizable

class PipelineProperties {

    private final Map context

    protected PipelineProperties(Map properties) {
        context = new HashMap(properties).withDefault { key -> throw new UnknownPipelineProperty(key) }
    }

    Object getAt(String key) {
        return context[key]
    }

    Object propertyMissing(String property) {
        return context[property]
    }

    private static class UnknownPipelineProperty extends RuntimeException {

        protected UnknownPipelineProperty(String key) {
            super("Pipeline property '$key' does not exists")
        }

    }

}
