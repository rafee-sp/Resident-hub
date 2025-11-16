
let accessToken = null;

export const tokenStore = {

    get : () => accessToken,
    set : (token) => {accessToken = token;},
    clear : () => {accessToken = null;},
    
}