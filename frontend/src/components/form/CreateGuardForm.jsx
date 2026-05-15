export default function CreateGuardForm({
                                            username,
                                            pin,
                                            onUsernameChange,
                                            onPinChange,
                                            onSubmit,
                                        }) {
    return (
        <form onSubmit={onSubmit}>
            <input
                placeholder="Username"
                value={username}
                onChange={onUsernameChange}
            />

            <input
                type="password"
                placeholder="PIN"
                value={pin}
                onChange={onPinChange}
            />

            <button className="primary" type="submit">
                Crear Guard
            </button>
        </form>
    );
}