# ShieldKey

A lightweight Fabric mod that adds a dedicated keybind for raising your shield.

Minecraft normally requires holding the **Use Item** button (right-click) to raise a shield. This works well with a mouse, but can be awkward for players using trackpads, accessibility devices, or those who simply prefer a separate shield key.

ShieldKey adds a configurable keybind that raises your offhand shield without changing vanilla combat mechanics.

## Features

- Dedicated shield keybind.
- Fully configurable key.
- Vanilla combat behavior.
- Client-side only.
- Lightweight with no gameplay changes.

## Why?

The goal of ShieldKey is **not** to make combat easier.

The goal is to improve ergonomics.

Players using laptops and trackpads often have difficulty holding right-click while simultaneously moving and aiming. ShieldKey simply allows those players to choose a more comfortable keybind.

## Planned Features

- [ ] Configurable shield key.
- [ ] Optional in-game configuration (Mod Menu / Cloth Config).
- [ ] Hold-to-block behavior.
- [ ] Compatibility with Fabric API.

## Installation

1. Install Fabric Loader.
2. Install Fabric API.
3. Place the ShieldKey `.jar` in your `mods` folder.
4. Launch Minecraft.

## Compatibility

- Fabric
- Client-side only

## License

MIT

---

# Revision History

## ShieldKey 0.1.0

Initial public beta release.

### Features

- Dedicated shield key
- Automatically lowers the shield when attacking
- Automatically restores the shield after attacking
- Right-click suspend and resume behavior
- Client-side Fabric mod

### Known Limitation

ShieldKey currently maintains shield use through Minecraft's standard Use input. When a usable or placeable item is held in the main hand, activating the shield may also use that item.

### Feedback and Bug Reports

Community feedback is welcome, especially regarding combat behavior and main-hand item interactions.

Please report [issues](https://github.com/Dreblow/ShieldKey/issues)

### Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API