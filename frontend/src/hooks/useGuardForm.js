import { useState } from "react";
import { generateCredential } from "../api/visitApi";
import { createVisitor, findVisitorByDni } from "../api/visitorApi";

const emptyForm = {
    dni: "",
    fullName: "",
    company: "",
    sector: "OPERACIONES",
    photo: null,
    photoUrl: "",
};

export function useGuardForm(loadDashboard) {
    const [form, setForm] = useState(emptyForm);
    const [visit, setVisit] = useState(null);
    const [loading, setLoading] = useState(false);

    function updateField(field, value) {
        setForm({
            ...form,
            [field]: value,
        });
    }

    async function searchVisitor() {
        try {
            const response = await findVisitorByDni(form.dni);

            setForm({
                ...form,
                fullName: response.data.fullName,
                company: response.data.company,
                sector: response.data.sector || "OPERACIONES",
                photoUrl: response.data.photoUrl || "",
                photo: null,
            });
        } catch {
            alert("Visitante no encontrado");
        }
    }

    async function registerVisitor() {
        try {
            setLoading(true);
            await createVisitor(form);
            alert("Visitante registrado");
            await loadDashboard();
        } catch {
            alert("No se pudo registrar visitante");
        } finally {
            setLoading(false);
        }
    }

    async function generateVisitCredential() {
        try {
            setLoading(true);

            const response = await generateCredential(
                form.dni,
                form.sector
            );

            setVisit(response.data);
            await loadDashboard();

            return response.data;
        } catch (error) {
            console.error(error.response?.data || error);
            alert("No se pudo generar credencial");
            return null;
        } finally {
            setLoading(false);
        }
    }

    function cleanForm() {
        setForm(emptyForm);
        setVisit(null);
    }

    return {
        form,
        visit,
        loading,
        updateField,
        searchVisitor,
        registerVisitor,
        generateVisitCredential,
        cleanForm,
    };
}