import { useState } from "react";
import { createGuard } from "../api/adminApi";
import Navbar from "../components/Navbar";
import AppShell from "../components/AppShell";

export default function AdminPage() {
    const [username, setUsername] = useState("");
    const [pin, setPin] = useState("");

    async function handleCreateGuard(event) {
        event.preventDefault();

        try {
            await createGuard(username, pin);

            alert("Guard creado correctamente");

            setUsername("");
            setPin("");
        } catch (error) {
            console.error(error.response?.data);
            alert(
                error.response?.data?.message ||
                "No se pudo crear el guard"
            );
        }
    }

    return (
        <AppShell>

            <section className="card">
                <h2 className="card-title">Admin Panel</h2>

                <form onSubmit={handleCreateGuard}>
                    <input
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />

                    <input
                        type="password"
                        placeholder="PIN"
                        value={pin}
                        onChange={(e) => setPin(e.target.value)}
                    />

                    <button className="primary" type="submit">
                        Crear Guard
                    </button>
                </form>
            </section>
        </AppShell>
    );
}