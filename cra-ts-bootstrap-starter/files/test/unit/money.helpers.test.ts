import {add, subtract} from '../../src/helpers/money.helpers'


describe('money helpers', () => {
    test('subtract', () => {
        expect(subtract(2.3, 2.4)).toEqual(-0.1);
    })
    test('add', () => {
        expect(add(2.3, 2.4)).toEqual(4.7);
    })
});