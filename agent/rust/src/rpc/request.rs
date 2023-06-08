use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize, Debug, PartialEq)]
#[serde(rename_all = "snake_case")]
#[serde(tag = "method", content = "params")]
pub enum CoreRequest {
    Config {
        open_ai_token: String,
        open_ai_url: Option<String>,
    },
    // completion_once
    CompletionOnce {
        document: Document
    }
}

#[derive(Serialize, Deserialize, Debug, PartialEq)]
#[serde(rename_all = "camelCase")]
pub struct Document {
    position: Position,
    use_spaces: bool,
    tab_size: u32,
    uri: String,
    version: u32,
}

#[derive(Serialize, Deserialize, Debug, PartialEq)]
#[serde(rename_all = "snake_case")]
pub struct Position {
    line: u32,
    character: u32,
}
