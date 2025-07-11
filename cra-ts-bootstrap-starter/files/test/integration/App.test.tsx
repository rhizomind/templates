import {render, screen} from '@testing-library/react';
import App from '../../src/App';
import {server} from "../mocks/server";
import {clientHandler} from "../mocks/msw.helpers";
import {setLogger} from "react-query";

describe('App', () => {
    beforeEach(()=>{
        setLogger({
            ...console,
            error: jest.fn()
        })
    })
    test('renders learn react link', () => {
        server.use(clientHandler({}))

        render(<App/>);

        const linkElement = screen.getByText(/Welcome to must-have-all-libs-template/i);
        expect(linkElement).toBeInTheDocument();
    });

})
