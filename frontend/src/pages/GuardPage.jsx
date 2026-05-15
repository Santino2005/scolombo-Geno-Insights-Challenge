import { useState } from "react";

import Navbar from "../components/Navbar";
import AppShell from "../components/AppShell";
import CredentialModal from "../components/CredentialModal";
import ScannerModal from "../components/camera/ScannerModal";
import VisitorForm from "../components/form/VisitorForm";
import DashboardPanel from "../components/DashboardPanel";

import { useGuardForm } from "../hooks/useGuardForm";
import { useGuardDashboard } from "../hooks/useGuardDashboard";

export default function GuardPage() {
    const [scannerOpen, setScannerOpen] = useState(false);
    const [credentialOpen, setCredentialOpen] = useState(false);

    const dashboard = useGuardDashboard();
    const guardForm = useGuardForm(dashboard.loadDashboard);

    async function handleGenerateCredential() {
        const visit = await guardForm.generateVisitCredential();

        if (visit) {
            setCredentialOpen(true);
        }
    }

    function closeScanner() {
        setScannerOpen(false);
        dashboard.loadDashboard();
    }

    return (
        <AppShell>
            <Navbar />

            <VisitorForm
                form={guardForm.form}
                visit={guardForm.visit}
                onUpdateField={guardForm.updateField}
                onSearchVisitor={guardForm.searchVisitor}
                onRegisterVisitor={guardForm.registerVisitor}
                onCleanForm={guardForm.cleanForm}
                onGenerateCredential={handleGenerateCredential}
                onOpenCredential={() => setCredentialOpen(true)}
                onOpenScanner={() => setScannerOpen(true)}
            />

            <DashboardPanel
                todayCount={dashboard.todayCount}
                totalVisitors={dashboard.totalVisitors}
                history={dashboard.history}
                onDownloadExcel={dashboard.downloadExcel}
            />

            {credentialOpen && guardForm.visit && (
                <CredentialModal
                    visit={guardForm.visit}
                    onClose={() => setCredentialOpen(false)}
                />
            )}

            {scannerOpen && (
                <ScannerModal onClose={closeScanner} />
            )}
        </AppShell>
    );
}