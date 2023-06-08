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
}
