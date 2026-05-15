import AppShell from "../components/AppShell";
import Navbar from "../components/Navbar";
import CredentialModal from "../components/CredentialModal";
import VisitorCredentialForm from "../components/VisitorCredentialForm";
import { useVisitorCredential } from "../hooks/useVisitorCredential";

export default function VisitorPage() {
    const visitorCredential = useVisitorCredential();

    return (
        <AppShell>
            <Navbar />

            <VisitorCredentialForm
                dni={visitorCredential.dni}
                sector={visitorCredential.sector}
                loading={visitorCredential.loading}
                onDniChange={(event) =>
                    visitorCredential.setDni(event.target.value)
                }
                onSectorChange={(event) =>
                    visitorCredential.setSector(event.target.value)
                }
                onGenerateCredential={
                    visitorCredential.handleGenerateCredential
                }
                onViewCredential={visitorCredential.viewCredential}
            />

            {visitorCredential.credentialOpen &&
                visitorCredential.visit && (
                    <CredentialModal
                        visit={visitorCredential.visit}
                        onClose={visitorCredential.closeCredential}
                    />
                )}
        </AppShell>
    );
}