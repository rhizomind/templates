import {devices} from "playwright";

const IS_CI = Boolean(process.env.CI)

const settings = IS_CI
    ? {}
    : {
        ...devices['Desktop Chrome'],
        baseUrl: 'http://localhost:9000/'
    }

export default {

    testDir: 'test/functional',
    outputDir: 'test/functiona/reports',
    reporter: [['list'], ['html', {outputFolder: './reports/functional', open: IS_CI ? 'nevern' : 'never'}]],

    projects: [
        {
            name: 'Desktop Chromium',
            use: {
                ...devices['Desktop Chrome'],
                ...settings
            }
        }
    ],

    webServer: {
        command: 'npm run start',
        port: 3000,
        timeout: 120 * 1000
    },

    use: {
        headless: true
    }
}