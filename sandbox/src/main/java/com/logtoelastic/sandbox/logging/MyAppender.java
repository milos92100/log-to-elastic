package com.logtoelastic.sandbox.logging;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;

@Plugin(
        name = "MyAppender",
        category = "Core",
        elementType = "appender",
        printObject = true
)
public class MyAppender extends AbstractAppender {


    protected MyAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static MyAppender createAppender(@PluginAttribute("name") String name,
                                            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                            @PluginElement("Layout") Layout layout,
                                            @PluginElement("Filters") Filter filter) {

        return new MyAppender(name, filter, layout, false, new Property[]{});
    }


    public void append(LogEvent logEvent) {
        System.out.println("MyAppender: " + logEvent.getContextData().getValue("correlationId") + ", " + logEvent.getMessage().getFormattedMessage());

    }
}
