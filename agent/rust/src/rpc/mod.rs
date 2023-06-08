use std::sync::{Mutex, Weak};

use serde_json::{self, json, Value};
use tracing::info;
use xi_rpc::{RemoteError, RpcPeer};

use client::Client;
use notification::CoreNotification;
use request::CoreRequest;

mod notification;
mod request;
pub mod unit_rpc;
mod client;

#[allow(dead_code)]
pub struct CoreState {
    peer: Client,
    token: Option<String>,
}

impl CoreState {
    pub(crate) fn new(peer: &RpcPeer) -> Self {
        CoreState {
            peer: Client::new(peer.clone()),
            token: None,
        }
    }

    // pub(crate) fn client_notification(&mut self, cmd: CoreNotification) {
    //     match cmd {
    //         CoreNotification::Initialize { .. } => {}
    //         CoreNotification::WorkspaceFileOpened { .. } => {}
    //         CoreNotification::WorkspaceFileClosed { .. } => {}
    //         CoreNotification::WorkspaceFileChanged { .. } => {}
    //         CoreNotification::Shutdown => {}
    //     }
    // }

    pub(crate) fn client_request(&mut self, cmd: CoreRequest) -> Result<Value, RemoteError> {
        use request::CoreRequest::*;
        match cmd {
            Config { open_ai_token, .. } => {
                self.token = Some(open_ai_token);
                Ok(json!({
                    "success": true
                }))
            }
            CompletionOnce { document } => {
                Ok(json!({
                    "status": "todo!()"
                }))
            }
        }
    }

    pub(crate) fn finish_setup(&mut self, _self_ref: WeakCoreState) {
        self.peer.send_client_started()
    }

    pub(crate) fn handle_idle(&mut self, token: usize) {
        match token {
            _ => {
                // info!("token: {}", token);
            }
        }
    }
}

/// A weak reference to the main state. This is passed to plugin threads.
#[derive(Clone)]
pub struct WeakCoreState(Weak<Mutex<CoreState>>);
