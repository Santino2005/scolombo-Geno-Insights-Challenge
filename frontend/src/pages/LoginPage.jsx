import LoginForm from "../components/form/LoginForm";
import { useLogin } from "../hooks/useLogin";

export default function LoginPage() {
    const login = useLogin();

    return (
        <main className="page">
            <section className="card">
                <h1>Control de Ingresos</h1>

                <LoginForm
                    username={login.username}
                    pin={login.pin}
                    onUsernameChange={(event) =>
                        login.setUsername(event.target.value)
                    }
                    onPinChange={(event) =>
                        login.setPin(event.target.value)
                    }
                    onSubmit={login.handleLogin}
                />

                <div className="divider" />

                <button
                    className="wide-button"
                    onClick={login.goToVisitorPortal}
                >
                    🎟️ Soy visitante
                </button>
            </section>
        </main>
    );
}