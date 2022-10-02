import Big from 'big.js'

export function add(a: number, b: number) : number {
    return Number(new Big(a).add(new Big(b)))
}


export function subtract(a: number, b: number) : number {
    return Number(new Big(a).minus(new Big(b)))
}