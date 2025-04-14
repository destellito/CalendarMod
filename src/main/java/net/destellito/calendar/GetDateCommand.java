package net.destellito.calendar;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class GetDateCommand {

    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };


    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            dispatcher.register(CommandManager.literal("getdate")
                    .executes(GetDateCommand::run));
        });
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        World world = source.getWorld();

        long day = world.getTimeOfDay() / 24000L; // 1 día en ticks = 24000

        long year = (day / 365) + 1;
        long dayOfYear = day % 365;

        int[] monthLengths = {
                31, 28, 31, 30, 31, 30,
                31, 31, 30, 31, 30, 31
        };

        int monthIndex = 0;
        int dayOfMonth = (int) dayOfYear + 1;

        for (int i = 0; i < 12; i++) {
            if (dayOfMonth > monthLengths[i]) {
                dayOfMonth -= monthLengths[i];
                monthIndex++;
            } else {
                break;
            }
        }

        String formattedDate = String.format("Date: %02d/%s/%02d",
                dayOfMonth,
                MONTHS[monthIndex],
                year
        );

        source.sendFeedback(() -> Text.literal(formattedDate), false);
        return Command.SINGLE_SUCCESS;
    }
}
