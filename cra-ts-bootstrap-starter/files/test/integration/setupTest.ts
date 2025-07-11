import '@testing-library/jest-dom';
import {configure} from "@testing-library/react";
import {server} from "../mocks/server";

configure({
    testIdAttribute: 'data-test-id'
});

beforeAll(() => {
    server.listen({onUnhandledRequest: 'error'})
})

afterEach(() => {
    server.resetHandlers()
})

afterAll(() => {
    server.close()
})