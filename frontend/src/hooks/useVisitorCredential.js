import { useState } from "react";
import {
    getActiveCredentialByDni,
    generateCredential,
} from "../api/visitApi";

export function useVisitorCredential() {
    const [dni, setDni] = useState("");
    const [sector, setSector] = useState("OPERACIONES");
    const [visit, setVisit] = useState(null);
    const [credentialOpen, setCredentialOpen] = useState(false);
    const [loading, setLoading] = useState(false);

    async function viewCredential() {
        if (!hasDni()) return;

        try {
            setLoading(true);

            const response = await getActiveCredentialByDni(dni);

            setVisit(response.data);
            setCredentialOpen(true);
        } catch {
            alert("No tenés una credencial activa");
        } finally {
            setLoading(false);
        }
    }

    async function handleGenerateCredential() {
        if (!hasDni()) return;

        try {
            setLoading(true);

            const response = await generateCredential(dni, sector);

            setVisit(response.data);
            setCredentialOpen(true);
        } catch (error) {
            console.error(error.response?.data || error);
            alert("No se pudo generar QR de ingreso. Verificá que estés registrado.");
        } finally {
            setLoading(false);
        }
    }

    function hasDni() {
        if (dni.trim()) return true;

        alert("Ingresá tu DNI");
        return false;
    }

    function closeCredential() {
        setCredentialOpen(false);
    }

    return {
        dni,
        sector,
        visit,
        credentialOpen,
        loading,
        setDni,
        setSector,
        viewCredential,
        handleGenerateCredential,
        closeCredential,
    };
}