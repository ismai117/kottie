import { instantiate } from './webApp-wasm.uninstantiated.mjs';

await wasmSetup;

instantiate({ skia: Module['asm'] });