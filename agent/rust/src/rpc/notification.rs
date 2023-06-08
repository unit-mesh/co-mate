use serde::{Deserialize, Serialize};
use std::path::PathBuf;

#[derive(Serialize, Deserialize, Debug, PartialEq)]
#[serde(rename_all = "snake_case")]
#[serde(tag = "method", content = "params")]
pub enum CoreNotification {
    WorkspaceFileOpened {
        uri: String,
        language_id: String,
        text: String,
    },
    WorkspaceFileClosed {
        uri: String,
    },
    WorkspaceFileChanged {
        uri: String,
        text: String,
    },
    Initialize {
        #[serde(default)]
        config_dir: Option<PathBuf>,
    },
    Shutdown,
}
