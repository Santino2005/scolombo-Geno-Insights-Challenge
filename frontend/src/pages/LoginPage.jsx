import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginGuard } from "../api/guardApi";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [pin, setPin] = useState("");
    const navigate = useNavigate();

    async function handleLogin(event) {
        event.preventDefault();

        try {
            if (username === "admin" && pin === "admin") {
                localStorage.setItem("adminLogged", "true");
                navigate("/admin");
                return;
            }

            await loginGuard(username, pin);

            localStorage.setItem("guardLogged", "true");
            navigate("/guard");
        } catch {
            alert("Credenciales inválidas");
        }
    }

    function goToVisitorPortal() {
        navigate("/visitor");
    }

    return (
        <main className="page">
            <section className="card">
                <h1>Control de Ingresos</h1>

                <form onSubmit={handleLogin}>
                    <input
                        placeholder="Usuario"
                        value={username}
                        onChange={(e) =>
                            setUsername(e.target.value)
                        }
                    />

                    <input
                        placeholder="PIN"
                        type="password"
                        value={pin}
                        onChange={(e) =>
                            setPin(e.target.value)
                        }
                    />

                    <button type="submit">
                        Ingresar como Guard
                    </button>
                </form>

                <div className="divider" />

                <button
                    className="wide-button"
                    onClick={goToVisitorPortal}
                >
                    🎟️ Soy visitante
                </button>
            </section>
        </main>
    );
}