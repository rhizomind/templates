import {rest} from "msw";

export function clientHandler(body: any) {
    return rest.get('/', async (req, res, ctx) => {
        return res(ctx.json(body))
    })
}