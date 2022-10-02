import {rest} from 'msw';

export const handlers = [
    rest.get('/', async (reg, res, ctx) => {
        return res(ctx.status(200))
    })
]