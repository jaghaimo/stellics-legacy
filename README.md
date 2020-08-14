# Stellar Logistics

Stellar Logistics mod adds a centralized storage (via Stellar Logistics Branch that enables a Stellar Logistics Warehouse submarket) and a hyperspace information network (StellNET).

## Features

### Branch and Warehouse

Stellar Logistics Warehouses are a new concept of interplanetary storage as a service.
The company behind it - the titular Stellar Logistics - offers access to its warehouses and courier network.
Pay one-time fee and enjoy unlimited access to your storage from any branch in the sector.
As it is paid-for service, you will have to pay maintenance fee based on the capacity your storage uses.

Stellar Logistics Branch allows access to the Stellar Logistics Warehouse from any market (e.g. planet, station) that has it built.
Disrupting the branch will temporarily remove the market from the courier network.
You can still access your storage from other branches though.

### Hyperspace Network

StellNET is a Stellar Logistics operated sector-wide hyperspace network that allows you to:
* Look for nearest operational Stellar Logistics Branch,
* Query markets for weapons, fighter wings, modspecs, or blueprints, and
* Search for officers with a given personality.

## Technical details

### Storage fee calculation

Stellar Logistics Warehouse Services, when not disrupted, offer full availability and accessibility.
The storage and handling fee depends on the size of the leased warehouse and not the value of stored cargo.
As such it is the perfect service to store high-value cargo.
Depending on the warehouse size the cost will vary between 1 and 9 credits per cargo hold per month.

### Vanilla and Nexerelin integration

All Independent markets of at least size 4 are seeded with Stellar Logistics Branches.
When used with [Nexerelin](https://fractalsoftworks.com/forum/index.php?topic=9175.0) the storage is located on the Prism Freeport (if enabled in a playthrough).

To add Stellar Logistics content to an existing game use [Console Commands](https://fractalsoftworks.com/forum/index.php?topic=4106.0) and run `stellicsInit`.

### Seeding options

Seeding options can be edited in the settings file ([stellics_settings.json](stellics_settings.json)).
These control which markets can, and will, have Stellar Logistics Branches and Warehouses created.

### Storage export and import

Additional two [Console Commands](https://fractalsoftworks.com/forum/index.php?topic=4106.0) allow for the movement of the contents of Stellar Logistics Warehouse between saves:
* `stellicsExport` - export contents of your storage to `stellics.csv` file, and
* `stellicsImport` - add contents of `stellics.csv` file to your storage.

Any items not recognized during import will be ignored, thus making this functionality mod-agnostic (e.g. can disable a mod between export and import).

## Third-party Resources

All third-party resources are listed below with their appropriate licenses.

| Description    | Modifications           | License     | Link |
| -------------- | ----------------------- | ----------- | ---- |
| Industry image | Cropped and sharpened   | Free to use. Photo by Gustavo Juliette from Pexels. | [pexels.com](https://www.pexels.com/photo/two-person-talking-on-stage-set-up-2473446/) |
| Ability image  | Cropped and sharpened   | Free to use. Photo by SpaceX from Pexels. | [pexels.com](https://www.pexels.com/photo/discovery-earth-nasa-research-23789/) |
| Storage icon   | Adjusted to match style | Free for personal and commercial purpose with attribution. Icons made by [bqlqn](https://www.flaticon.com/authors/bqlqn) from [Flaticon](https://www.flaticon.com/). | [flaticon.com](https://www.flaticon.com/free-icon/box_3037005) |

## Special Thanks

* [@jstaf](https://github.com/jstaf) for the [Mayorate](https://github.com/jstaf/mayorate) on which I based my project (e.g. layout, build, and release scripts)
* The merry people of [Unofficial Starsector Discord](https://discord.gg/TBhcFNh) for answering any and all of my questions
