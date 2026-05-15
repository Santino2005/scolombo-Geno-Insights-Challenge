import AppShell from "../components/AppShell";
import CreateGuardForm from "../components/form/CreateGuardForm";
import { useCreateGuard } from "../hooks/useCreateGuard";

export default function AdminPage() {
    const guardForm = useCreateGuard();

    return (
        <AppShell>
            <section className="card">
                <h2 className="card-title">Admin Panel</h2>

                <CreateGuardForm
                    username={guardForm.username}
                    pin={guardForm.pin}
                    onUsernameChange={(event) =>
                        guardForm.setUsername(event.target.value)
                    }
                    onPinChange={(event) =>
                        guardForm.setPin(event.target.value)
                    }
                    onSubmit={guardForm.handleCreateGuard}
                />
            </section>
        </AppShell>
    );
}