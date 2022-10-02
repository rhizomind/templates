module.exports = {
    verbose: true,
    providers: [
        {
            provider: 'random-provider',
            files: ['./src/api/rest/**/*.{ts,tsx}'],
            responseHeaders: {
                'Content-Type': 'application/json'
            }
        }
    ],
    consumer: '{{artifactId}}'
}