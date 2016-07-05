import groovy.xml.MarkupBuilder

def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.message {
    greeting 'Hello Confitura'
    motd {
        abbreviated 'Automate!'
        full 'Automate all the things!'
    }
}

println writer.toString()