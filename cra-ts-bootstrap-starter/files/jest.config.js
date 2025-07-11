const IS_CI = true; //Boolean(process.env.CI)

module.exports = {
    collectCoverage: IS_CI,
    collectCoverageFrom: ['./src/**/*.{js,jsx,ts,tsx}'],

    reporters: [
        'default',
        ...(IS_CI
                ? [
                    [
                        'jest-junit',
                        {
                            outputDirectory: './reports',
                            outputName: 'junit.xml',
                            classNameTemplate: '{classname}',
                            titleTemplate: '{title}',
                            ancestorSeparator: ' ',
                            suiteNameTemplate: '{filename}'
                        }
                    ],
                    [
                        'jest-sonar',
                        {
                            outputDirectory: './reports',
                            outputName: 'test-report.xml',
                        }
                    ]
                ]
                : []
        )
    ],

    projects: [
        {
            displayName: 'unit',
            preset: 'ts-jest',
            testEnvironment: 'node',
            roots: ['test/unit'],
            resetMocks: true,
            clearMocks: true
        },
        {
            displayName: 'int',
            preset: 'ts-jest',
            testEnvironment: 'jsdom',
            roots: ['test/integration'],
            setupFilesAfterEnv: ['./test/integration/setupTest.ts'],
            restoreMocks: true,
            clearMocks: true,
            moduleNameMapper: {
                '\\.(css|less)$': 'identity-obj-proxy',
                "^.+\\.(svg|png)$": "jest-svg-transformer"
            }
        }
    ]
}