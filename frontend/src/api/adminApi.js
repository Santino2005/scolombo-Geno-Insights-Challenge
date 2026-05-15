import { api } from "./client";

export function createGuard(username, pin) {
    return api.post("/admin/guard/create", null, {
        params: {
            username,
            pin,
        },
    });
}