# Stellar Logistics

Courier and warehousing services for Starsector.

Stellar Logistics adds a new structure (Stellar Logistics Branch) and sub-market (Stellar Logistics Warehouse).

Stellar Logistics Warehouses are a new concept of interplanetary storage as a service.
The company behind it - titular Stellar Logistics - offers access to its courier and warehousing services.
Pay one-time access fee and enjoy unlimited access to your storage from any branch in the sector.
Do note however that you will have to pay for the capacity of your storage as it is a paid-for service.

Stellar Logistics Branch enables the Stellar Logistics Warehouse sub-market (storage) on the market (e.g. planet, station) it is built.
Actual storage is located on the market that build the first branch.
Disrupting a branch will temporarily remove the market from the courier network.

## Fee calculation

Stellar Logistics Warehouse Services, when not disrupted, offer full availability and accessibility.
The storage and handling fee depends on the size of the leased warehouse and not the value of stored cargo.
As such it is the perfect service to store high-value cargo.
Depending on the warehouse size the cost will vary between 1 and 6 credits per cargo hold per month.

## Vanilla and Nexerelin integration

All Independent markets of at least size 4 are seeded with Stellar Logistics Branches.
When used with [Nexerelin](https://fractalsoftworks.com/forum/index.php?topic=9175.0) the storage is located on the Prism Freeport (if enabled in a playthrough).

To add Stellar Logistics content to an existing game use [Console Commands](https://fractalsoftworks.com/forum/index.php?topic=4106.0) and run `stellicsInit`.

## Seeding options

Seeding options can be edited in the settings file ([stellics_settings.json](stellics_settings.json)).
These control which markets can, and will, have Stellar Logistics Branch and Warehouse created.

## Licenses and Resources

All original code is distributed under MIT license.
All third-party resources are listed below with their appropriate licenses.

### Third-party Resources

| Description    | Modifications           | License     | Link |
| -------------- | ----------------------- | ----------- | ---- |
| Industry image | Cropped and sharpened   | Free to use. Photo by Gustavo Juliette from Pexels. | [pexels.com](https://www.pexels.com/photo/two-person-talking-on-stage-set-up-2473446/) |
| Storage image  | Adjusted to match style | Free for personal and commercial purpose with attribution. Icons made by [bqlqn](https://www.flaticon.com/authors/bqlqn) from [Flaticon](https://www.flaticon.com/). | [flaticon.com](https://www.flaticon.com/free-icon/box_3037005) |

## Special Thanks

* [@jstaf](https://github.com/jstaf) for the [Mayorate](https://github.com/jstaf/mayorate) on which I based my project (layout, build, and release scripts)
* [@SirHartley#5459](https://discord.gg/TBhcFNh) for answering any and all of my questions
