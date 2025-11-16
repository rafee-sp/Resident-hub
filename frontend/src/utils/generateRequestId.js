export const generateRequestId = () => {
    return crypto.randomUUID?.() || Date.now().toString();
}
