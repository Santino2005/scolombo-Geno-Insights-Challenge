import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginGuard } from "../api/guardApi";

export function useLogin() {
    const [username, setUsername] = useState("");
    const [pin, setPin] = useState("");
    const navigate = useNavigate();

    async function handleLogin(event) {
        event.preventDefault();

        try {
            if (isAdminLogin()) {
                loginAsAdmin();
                return;
            }

            await loginGuard(username, pin);
            loginAsGuard();
        } catch {
            alert("Credenciales inválidas");
        }
    }

    function isAdminLogin() {
        return username === "admin" && pin === "admin";
    }

    function loginAsAdmin() {
        localStorage.setItem("adminLogged", "true");
        navigate("/admin");
    }

    function loginAsGuard() {
        localStorage.setItem("guardLogged", "true");
        navigate("/guard");
    }

    function goToVisitorPortal() {
        navigate("/visitor");
    }

    return {
        username,
        pin,
        setUsername,
        setPin,
        handleLogin,
        goToVisitorPortal,
    };
}