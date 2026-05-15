import { api } from "./client";

export function generateCredential(dni, sector) {
    return api.post("/visit", {
        dni,
        sector,
    });
}

export function scanVisitQr(qrToken) {
    return api.put(`/visit/scan/${qrToken}`);
}

export function getCredential(qrToken) {
    return api.get(`/visit/credential/${qrToken}`);
}

export function getActiveCredentialByDni(dni) {
    return api.get(`/visit/credential/active/${dni}`);
}

export function getTodayVisits() {
    return api.get("/visit/today");
}

export function getVisitHistory() {
    return api.get("/visit/history");
}

export function downloadVisitHistoryExcel() {
    return api.get("/visit/history/export", {
        responseType: "blob",
    });
}