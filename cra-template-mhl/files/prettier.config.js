module.exports = {
    singleQuote: true,
    tabWidth: 4,
    trailingComma: "all",
    printWidth: 140,
    bracketSpacing: false,
    overrides: [
        {
            files: ['kubernetes/**/*'],
            options: {
                tabWidth: 2
            }
        }
    ]
}