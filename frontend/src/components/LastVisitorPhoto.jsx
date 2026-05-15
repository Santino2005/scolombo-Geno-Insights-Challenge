export default function LastVisitorPhoto({ photoUrl, hasNewPhoto }) {
    if (!photoUrl || hasNewPhoto) return null;

    return (
        <div className="last-photo-box">
            <p>Última foto registrada</p>

            <img
                src={photoUrl}
                alt="Última foto del visitante"
                className="last-visitor-photo"
            />
        </div>
    );
}