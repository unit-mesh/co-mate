use serde_json::json;
use xi_rpc::RpcPeer;

pub struct Client(pub(crate) RpcPeer);

const VERSION: &str = env!("CARGO_PKG_VERSION");

impl Client {
    pub fn new(peer: RpcPeer) -> Self {
        Client(peer)
    }

    pub fn send_client_started(&self) {
        self.0.send_rpc_notification(
            "client_started",
            &json!({
                "version": VERSION,
            }),
        );
    }

    pub(crate) fn set_open_ai_token(&self, p0: String) {
        self.0.send_rpc_request(
            "config",
            &json!({
                "open_ai_token": p0,
            }),
        ).expect("TODO: panic message");
    }
}
