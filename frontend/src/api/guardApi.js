import { api } from "./client";

export function loginGuard(username, pin) {
    return api.post("/guard/login", {
        username,
        pin,
    });
}