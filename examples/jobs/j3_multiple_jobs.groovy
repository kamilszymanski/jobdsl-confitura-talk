job('hello-confitura') {
    steps {
        shell 'echo "Hello Confitura!"'
    }
}

job('hello-world') {
    steps {
        shell 'echo "Hello World!"'
    }
}