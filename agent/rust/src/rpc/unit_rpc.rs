use std::sync::{Arc, Mutex, MutexGuard};

use serde_json::Value;
use xi_rpc::{Handler, RemoteError, RpcCtx};

use crate::rpc::{CoreState, WeakCoreState};
use crate::rpc::notification::CoreNotification;
use crate::rpc::notification::CoreNotification::Initialize;
use crate::rpc::request::CoreRequest;

pub enum UnitRpc {
    // TODO: profile startup, and determine what things (such as theme loading)
    // we should be doing before client_init.
    Waiting,
    Running(Arc<Mutex<CoreState>>),
}

#[allow(dead_code)]
impl UnitRpc {
    pub fn new() -> Self {
        UnitRpc::Waiting
    }

    /// Returns `true` if the `client_started` has not been received.
    fn is_waiting(&self) -> bool {
        match *self {
            UnitRpc::Waiting => true,
            _ => false,
        }
    }

    /// Returns a guard to the core state. A convenience around `Mutex::lock`.
    ///
    /// # Panics
    ///
    /// Panics if core has not yet received the `client_started` message.
    pub fn inner(&self) -> MutexGuard<CoreState> {
        match self {
            UnitRpc::Running(ref inner) => inner.lock().unwrap(),
            UnitRpc::Waiting => panic!(
                "core does not start until client_started \
                 RPC is received"
            ),
        }
    }

    /// Returns a new reference to the core state, if core is running.
    fn weak_self(&self) -> Option<WeakCoreState> {
        match self {
            UnitRpc::Running(ref inner) => Some(WeakCoreState(Arc::downgrade(inner))),
            UnitRpc::Waiting => None,
        }
    }
}

impl Handler for UnitRpc {
    type Notification = CoreNotification;
    type Request = CoreRequest;

    fn handle_notification(&mut self, ctx: &RpcCtx, rpc: Self::Notification) {
        // We allow tracing to be enabled before event `client_started`
        match rpc {
            Initialize { ref config_dir, } => {
                assert!(self.is_waiting(), "client_started can only be sent once");
                let state = CoreState::new(ctx.get_peer());
                let state = Arc::new(Mutex::new(state));
                *self = UnitRpc::Running(state);
                let weak_self = self.weak_self().unwrap();
                self.inner().finish_setup(weak_self);
            }
            CoreNotification::WorkspaceFileOpened { .. } => {}
            CoreNotification::WorkspaceFileClosed { .. } => {}
            CoreNotification::WorkspaceFileChanged { .. } => {}
            CoreNotification::Shutdown => {}
        }
        // self.inner().client_notification(rpc);
    }

    fn handle_request(&mut self, _ctx: &RpcCtx, rpc: Self::Request) -> Result<Value, RemoteError> {
        self.inner().client_request(rpc)
    }

    fn idle(&mut self, _ctx: &RpcCtx, token: usize) {
        self.inner().handle_idle(token);
    }
}
