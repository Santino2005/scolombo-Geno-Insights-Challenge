import { useState } from "react";
import { createGuard } from "../api/adminApi";

export function useCreateGuard() {
    const [username, setUsername] = useState("");
    const [pin, setPin] = useState("");

    async function handleCreateGuard(event) {
        event.preventDefault();

        try {
            await createGuard(username, pin);
            handleSuccess();
        } catch (error) {
            handleError(error);
        }
    }

    function handleSuccess() {
        alert("Guard creado correctamente");
        setUsername("");
        setPin("");
    }

    function handleError(error) {
        console.error(error.response?.data);

        alert(
            error.response?.data?.message ||
            "No se pudo crear el guard"
        );
    }

    return {
        username,
        pin,
        setUsername,
        setPin,
        handleCreateGuard,
    };
}