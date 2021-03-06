/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.sample.client;

import com.ponysdk.core.model.PUnit;
import com.ponysdk.core.server.application.UIContext;
import com.ponysdk.core.server.concurrent.PScheduler;
import com.ponysdk.core.server.stm.Txn;
import com.ponysdk.core.ui.basic.*;
import com.ponysdk.core.ui.basic.event.PClickEvent;
import com.ponysdk.core.ui.basic.event.PKeyUpEvent;
import com.ponysdk.core.ui.basic.event.PKeyUpHandler;
import com.ponysdk.core.ui.datagrid.ColumnDescriptor;
import com.ponysdk.core.ui.datagrid.DataGrid;
import com.ponysdk.core.ui.datagrid.dynamic.Configuration;
import com.ponysdk.core.ui.datagrid.dynamic.DynamicDataGrid;
import com.ponysdk.core.ui.datagrid.impl.PLabelCellRenderer;
import com.ponysdk.core.ui.eventbus2.EventBus.EventHandler;
import com.ponysdk.core.ui.formatter.TextFunction;
import com.ponysdk.core.ui.grid.AbstractGridWidget;
import com.ponysdk.core.ui.grid.GridTableWidget;
import com.ponysdk.core.ui.list.DataGridColumnDescriptor;
import com.ponysdk.core.ui.list.refreshable.Cell;
import com.ponysdk.core.ui.list.refreshable.RefreshableDataGrid;
import com.ponysdk.core.ui.list.renderer.cell.CellRenderer;
import com.ponysdk.core.ui.list.valueprovider.IdentityValueProvider;
import com.ponysdk.core.ui.main.EntryPoint;
import com.ponysdk.core.ui.model.PKeyCodes;
import com.ponysdk.core.ui.rich.PConfirmDialog;
import com.ponysdk.core.ui.rich.POptionPane;
import com.ponysdk.core.ui.rich.PToolbar;
import com.ponysdk.core.ui.rich.PTwinListBox;
import com.ponysdk.sample.client.event.UserLoggedOutEvent;
import com.ponysdk.sample.client.event.UserLoggedOutHandler;
import com.ponysdk.sample.client.page.addon.LoggerAddOn;
import com.ponysdk.core.ui.scene.AbstractScene;
import com.ponysdk.core.ui.scene.Router;
import com.ponysdk.core.ui.scene.Scene;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UISampleEntryPoint implements EntryPoint, UserLoggedOutHandler {

    private PLabel mainLabel;

    // HighChartsStackedColumnAddOn highChartsStackedColumnAddOn;
    int a = 0;

    private static int counter;

    @Override
    public void start(final UIContext uiContext) {

        uiContext.setTerminalDataReceiver((object, instruction) -> System.err.println(object + " : " + instruction));

        //createReconnectingPanel();

        mainLabel = Element.newPLabel("Can be dd by anybody : ₲ῳ₸");
        mainLabel.setAttributeLinkedToValue("data-title");
        mainLabel.setTitle("String ASCII");

        //PWindow.getMain().add(mainLabel);
        //testVisibilityHandler(PWindow.getMain());
        //testPerf();
        //testScene();
        //if (true) return;
        //createNewGridSystem();
        //testPAddon();
        //createWindow().open();
        //downloadFile();
        //createNewEvent();
        //testUIDelegator();
        //testNewGrid();
        //createFunctionalLabel();
        //PWindow.getMain().add(createGrid());
        //       testPAddon();

        //PScript.execute(PWindow.getMain(), "alert('coucou Main');");

        final PWindow window = createWindow();
        window.open();

        PWindow.getMain().add(Element.newA());

        // PWindow.getMain().add(new PHistory());
        // PWindow.getMain().add(new PNotificationManager());
        // PWindow.getMain().add(new PSuggestBox());

        PWindow.getMain().add(createBlock(createAbsolutePanel()));
        // PWindow.getMain().add(createPAddOn().asWidget());
        PWindow.getMain().add(Element.newPAnchor());
        PWindow.getMain().add(Element.newPAnchor("Anchor"));
        PWindow.getMain().add(Element.newPAnchor("Anchor 1", "anchor2"));
        PWindow.getMain().add(Element.newPButton());
        PWindow.getMain().add(createButton());
        PWindow.getMain().add(Element.newPCheckBox());
        PWindow.getMain().add(Element.newPCheckBox("Checkbox"));
        final PCookies cookies = new PCookies();
        cookies.setCookie("Cook", "ies");
        PWindow.getMain().add(createDateBox());
        PWindow.getMain().add(Element.newPDateBox(new SimpleDateFormat("dd/MM/yyyy")));
        PWindow.getMain().add(Element.newPDateBox(Element.newPDatePicker(), new SimpleDateFormat("yyyy/MM/dd")));

        final PDateBox dateBox = Element.newPDateBox(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS"), true);
        dateBox.addValueChangeHandler(event -> System.err.println(event.getData()));
        PWindow.getMain().add(dateBox);

        PWindow.getMain().add(Element.newPDatePicker());
        PWindow.getMain().add(Element.newPDecoratedPopupPanel(false));
        PWindow.getMain().add(Element.newPDecoratedPopupPanel(true));
        PWindow.getMain().add(Element.newPDecoratorPanel());
        PWindow.getMain().add(Element.newPDialogBox());
        PWindow.getMain().add(Element.newPDialogBox(true));
        PWindow.getMain().add(Element.newPDisclosurePanel("Disclosure"));
        PWindow.getMain().add(createDockLayoutPanel());
        final PFrame frame = Element.newPFrame("http://localhost:8081/sample/");
        frame.add(Element.newPLabel("Inside the frame"));
        PWindow.getMain().add(frame);
        PWindow.getMain().add(createPFileUpload());
        PWindow.getMain().add(Element.newPFlexTable());
        PWindow.getMain().add(createPFlowPanel());
        PWindow.getMain().add(Element.newPFocusPanel());
        PWindow.getMain().add(Element.newPGrid());
        PWindow.getMain().add(Element.newPGrid(2, 3));
        PWindow.getMain().add(Element.newPHeaderPanel());
        // PWindow.getMain().add(new PHistory());
        PWindow.getMain().add(Element.newPHorizontalPanel());
        PWindow.getMain().add(Element.newPHTML());
        PWindow.getMain().add(Element.newPHTML("Html"));
        PWindow.getMain().add(Element.newPHTML("Html 1", true));
        PWindow.getMain().add(Element.newPImage()); // FIXME Test with image
        PWindow.getMain().add(Element.newPLabel());
        PWindow.getMain().add(Element.newPLabel("Label"));
        PWindow.getMain().add(Element.newPLayoutPanel());
        PWindow.getMain().add(Element.newPListBox());
        PWindow.getMain().add(createListBox());
        PWindow.getMain().add(Element.newPMenuBar());
        PWindow.getMain().add(createMenu());
        // PWindow.getMain().add(new PNotificationManager());
        PWindow.getMain().add(Element.newPPasswordTextBox());
        PWindow.getMain().add(Element.newPPasswordTextBox("Password"));

        PWindow.getMain().add(Element.newPPopupPanel());
        PWindow.getMain().add(Element.newPPopupPanel(true));

        PWindow.getMain().add(Element.newPPushButton(Element.newPImage())); // FIXME Test with image

        PWindow.getMain().add(Element.newPRadioButton("RadioLabel"));
        PWindow.getMain().add(Element.newPRadioButton("RadioLabel"));
        final PRichTextArea richTextArea = Element.newPRichTextArea();
        PWindow.getMain().add(richTextArea);
        PWindow.getMain().add(Element.newPRichTextToolbar(richTextArea));
        PWindow.getMain().add(Element.newPScrollPanel());
        PWindow.getMain().add(Element.newPSimpleLayoutPanel());
        PWindow.getMain().add(Element.newPSimplePanel());
        PWindow.getMain().add(Element.newPSplitLayoutPanel());
        PWindow.getMain().add(createStackLayoutPanel());
        // PWindow.getMain().add(new PSuggestBox());
        PWindow.getMain().add(createTabLayoutPanel());
        PWindow.getMain().add(Element.newPTabPanel());
        PWindow.getMain().add(Element.newPTextArea());
        PWindow.getMain().add(createPTextBox());
        PWindow.getMain().add(new PToolbar());
        PWindow.getMain().add(createTree());
        PWindow.getMain().add(new PTwinListBox<>());
        PWindow.getMain().add(Element.newPVerticalPanel());

        mainLabel = Element.newPLabel("Label2");
        mainLabel.addClickHandler(event -> System.out.println("bbbbb"));
        PWindow.getMain().add(mainLabel);

        try {
            window.add(mainLabel);
        } catch (final Exception e) {
        }

        PWindow.getMain().add(Element.newPLabel("Label3"));

        final PLabel label = Element.newPLabel("Label4");

        try {
            window.add(label);
            PWindow.getMain().add(label);
        } catch (final Exception e) {
        }

        PConfirmDialog.show(PWindow.getMain(), "AAA", Element.newPLabel("AA"));

        POptionPane.showConfirmDialog(PWindow.getMain(), null, "BBB");

        // uiContext.getHistory().newItem("", false);
    }

    private void testScene() {
        Scene scene1 = new AbstractScene("scene1", "Scene 1", "scene1") {
            @Override
            public PWidget buildGUI() {
                return Element.newPLabel("Scene1 Started");
            }
        };

        Scene scene2 = new AbstractScene("scene2", "Scene 2", "scene2") {
            @Override
            public PWidget buildGUI() {
                return Element.newPLabel("Scene2 Started");
            }
        };

        PSimplePanel layout = Element.newPSimplePanel();
        Router router = new Router("sample");
        router.setLayout(layout);
        router.push(scene1);
        router.push(scene2);

        PWindow.getMain().add(layout);

        router.go("scene2");
    }

    private void testVisibilityHandler(final PWindow window) {
        final PLabel liveVisibility = Element.newPLabel("Live Visibility : Unknown");
        window.add(liveVisibility);

        final PButton button = Element.newPButton("Check visibility");
        window.add(button);

        final PLabel visibilityLabel = Element.newPLabel("Visibility : Unknown");
        window.add(visibilityLabel);

        final PScrollPanel frame = Element.newPScrollPanel();
        frame.setHeight("200px");
        frame.setWidth("300px");
        window.add(frame);

        final PFlowPanel panel = Element.newPFlowPanel();
        panel.setHeight("2000px");
        frame.add(panel);

        final PFlowPanel subPanel = Element.newPFlowPanel();
        subPanel.setStyleProperty("backgroundColor", "red");
        subPanel.setHeight("125px");
        subPanel.setWidth("200px");
        panel.add(subPanel);

        final PLabel label = Element.newPLabel("Increment : " + a++);
        subPanel.add(label);

        PScheduler.scheduleAtFixedRate(() -> {
            a++;
            if (subPanel.isShown() && subPanel.getWindow().isShown()) updateLabel(label, String.valueOf(a));
        }, Duration.ofSeconds(1));

        subPanel.getWindow().addVisibilityHandler(event -> {
            if (event.getData()) {
                System.err.println("Force refresh, because window became visible");
                updateLabel(label, String.valueOf(a));
            } else {
                System.err.println("Window became not visible");
            }
        });

        liveVisibility.setText("Live Visibility : " + subPanel.isShown());
        visibilityLabel.setText("Visibility : " + subPanel.isShown());
        subPanel.addVisibilityHandler(event -> {
            liveVisibility.setText("Live Visibility : " + event.getData());
            if (event.getData()) {
                System.err.println("Force refresh, because panel became visible");
                updateLabel(label, String.valueOf(a));
            }
        });
        button.addClickHandler(event -> {
            visibilityLabel.setText("Visibility : " + subPanel.isShown());
        });
    }

    private static void updateLabel(final PLabel label, final String text) {
        System.out.println("Update label " + text);
        label.setText("Increment : " + text);
    }

    private void createFunctionalLabel() {
        final TextFunction textFunction = new TextFunction(args -> {
            System.out.println(args[0] + " " + args[1]);
            return (String) args[0];
        }, "console.log(args[0] + \" \" + args[1]); return args[0];");
        final PFunctionalLabel newPFunctionalLabel = Element.newPFunctionalLabel(textFunction);
        PWindow.getMain().add(newPFunctionalLabel);
        newPFunctionalLabel.setArgs("A", "B");
    }

    public PFlowPanel createPFileUpload() {
        final PFlowPanel panel = Element.newPFlowPanel();
        final PFileUpload fileUpload = Element.newPFileUpload();
        fileUpload.setName("file");
        panel.add(fileUpload);
        fileUpload.addChangeHandler(event -> {
            final PFileUpload pFileUpload = (PFileUpload) event.getSource();
            System.out.println("File name : " + pFileUpload.getFileName());
            System.out.println("File size : " + pFileUpload.getFileSize() + " bytes");
        });
        fileUpload.addStreamHandler((request, response, context) -> {
            try {
                final List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (final FileItem item : items) {
                    if (!item.isFormField()) readFileItem(item);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
        final PButton button = Element.newPButton("Submit");
        button.addClickHandler(event -> fileUpload.submit());
        panel.add(button);
        return panel;
    }

    private void readFileItem(final FileItem item) throws IOException, FileNotFoundException {
        // Store the uploaded file on the server (don't forget to remove)
        final String fileName = FilenameUtils.getName(item.getName());
        final InputStream fileContent = item.getInputStream();
        final File uploadedFile = File.createTempFile(fileName, "fileUpload");
        IOUtils.copy(fileContent, new FileOutputStream(uploadedFile));
        uploadedFile.deleteOnExit();

        // Read directly the input stream
        final BufferedReader reader = new BufferedReader(new InputStreamReader(item.getInputStream(), "UTF-8"));
        final StringBuilder value = new StringBuilder();
        final char[] buffer = new char[1024];
        for (int length = 0; (length = reader.read(buffer)) > 0; ) {
            value.append(buffer, 0, length);
        }
        System.out.println(value.toString());
    }

    private void testPerf() {
        final PWindow w = Element.newPWindow("Window 1", "resizable=yes,location=0,status=0,scrollbars=0");
        final List<PLabel> labels = new ArrayList<>(1000);

        final PButton start = Element.newPButton("Start");
        w.add(start);
        start.addClickHandler(event -> scheduleUpdate(labels));

        for (int i = 0; i < 1000; i++) {
            final PLabel label = Element.newPLabel(counter + "-" + i);
            labels.add(label);
            w.add(label);
        }

        w.open();
    }

    private void scheduleUpdate(final List<PLabel> labels) {
        PScheduler.schedule(() -> {
            int i = 0;
            counter++;
            for (final PLabel label : labels) {
                label.setText(counter + "-" + i);
                i++;
            }
            if (counter < 20) scheduleUpdate(labels);
            else counter = 0;
        }, Duration.ofMillis(20));
    }

    private void createNewGridSystem() {
        //        final DataGrid<Pojo> grid = new DataGrid<>((a, b) -> a.bid.compareTo(b.bid));

        final Configuration<Pojo> configuration = new Configuration<>(Pojo.class);
        //configuration.setFilter(method -> method.getName().contains("COUCOU"));

        final DataGrid<Pojo> grid = new DynamicDataGrid<>(configuration, Comparator.comparing(Pojo::getBid));

        PWindow.getMain().add(grid);

        final Random random = new Random();

        final Map<String, Pojo> map = new HashMap<>();

        for (int i = 0; i < 40; i++) {
            final Pojo pojo = new Pojo();
            pojo.security = "security" + i;
            pojo.classe = "class" + i;
            pojo.bid = random.nextDouble() * i;
            pojo.offer = random.nextDouble() * i;
            pojo.spread = random.nextDouble() * i;
            pojo.coucou = random.nextDouble() * i + "";
            pojo.coucou1 = random.nextDouble() * i + "";
            pojo.coucou2 = random.nextDouble() * i + "";
            pojo.coucou3 = random.nextDouble() * i + "";
            pojo.coucou4 = random.nextDouble() * i + "";
            pojo.coucou5 = random.nextDouble() * i + "";
            pojo.coucou6 = random.nextDouble() * i + "";
            pojo.coucou7 = random.nextDouble() * i + "";
            pojo.coucou8 = random.nextDouble() * i + "";
            pojo.coucou9 = random.nextDouble() * i + "";
            pojo.coucou10 = random.nextDouble() * i + "";
            map.put("security" + i, pojo);
            grid.addData(pojo);
        }

        Txn.get().flush();

        PScheduler.scheduleAtFixedRate(() -> {
            for (int i = 0; i < 40; i++) {
                final Pojo pojo = map.get("security" + i);
                grid.update(pojo, p -> {
                    p.bid = random.nextDouble();
                    p.offer = random.nextDouble();
                    p.spread = random.nextDouble();
                    return p;
                });
            }
        }, Duration.ofMillis(300));
    }

    private void createReconnectingPanel() {
        final PSimplePanel reconnectionPanel = Element.newPSimplePanel();
        reconnectionPanel.setAttribute("id", "reconnection");
        final PSimplePanel reconnectingPanel = Element.newPSimplePanel();
        reconnectingPanel.setAttribute("id", "reconnecting");
        reconnectionPanel.setWidget(reconnectingPanel);
        PWindow.getMain().add(reconnectionPanel);
    }

    private void downloadFile() {
        final PButton downloadImageButton = Element.newPButton("Download Pony image");
        downloadImageButton.addClickHandler(event -> UIContext.get().stackStreamRequest((request, response, uiContext1) -> {
            response.reset();
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "attachment; filename=pony_image.png");

            try {
                final OutputStream output = response.getOutputStream();
                final InputStream input = getClass().getClassLoader().getResourceAsStream("images/pony.png");

                final byte[] buff = new byte[1024];
                while (input.read(buff) != -1) {
                    output.write(buff);
                }

                output.flush();
                output.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }));
        PWindow.getMain().add(downloadImageButton);
    }

    private void testPAddon() {
        final LoggerAddOn addon = createPAddOn();
        addon.attach(PWindow.getMain());

        // final PElementAddOn elementAddOn = new PElementAddOn();
        // elementAddOn.setInnerText("Coucou");
        // flowPanel.add(elementAddOn);

        // highChartsStackedColumnAddOn = new HighChartsStackedColumnAddOn();
        // PWindow.getMain().add(highChartsStackedColumnAddOn);
        // highChartsStackedColumnAddOn.setSeries("");

        // final HighChartsStackedColumnAddOn h2 = new
        // HighChartsStackedColumnAddOn();
        // a.add(h2);
        // h2.setSeries("");
        // final PElementAddOn elementAddOn2 = new PElementAddOn();
        // elementAddOn2.setInnerText("Coucou dans window");
        // a.add(elementAddOn2);
    }

    private void testNewGrid() {
        final AtomicInteger i = new AtomicInteger();

        final DataGrid<Integer> grid = new DataGrid<>();

        for (int cpt = 0; cpt < 20; cpt++) {
            final ColumnDescriptor<Integer> column = new ColumnDescriptor<>();
            final PAnchor anchor = Element.newPAnchor("Header " + i.incrementAndGet());
            anchor.addClickHandler(e -> grid.removeColumn(column));
            column.setCellRenderer(new PLabelCellRenderer<>(from -> a + ""));
            column.setHeaderRenderer(() -> anchor);
            grid.addColumnDescriptor(column);
        }
        final PTextBox textBox = Element.newPTextBox();

        //        final PButton add = Element.newPButton("add");
        //        add.addClickHandler(e -> grid.setData(Integer.valueOf(textBox.getText())));
        //        PWindow.getMain().add(add);

        PWindow.getMain().add(textBox);
        PWindow.getMain().add(grid);

        /**
         * PScheduler.scheduleAtFixedRate(() -> { grid.setData((int)
         * (Math.random() * 50)); grid.removeData((int) (Math.random() * 50));
         * grid.removeColumn(grid.getColumns().get((int) (Math.random() *
         * grid.getColumns().size() - 1)));
         *
         * final ColumnDescriptor<Integer> column = new ColumnDescriptor<>();
         * final PAnchor anchor = new PAnchor("Header " + id.incrementAndGet());
         * anchor.addClickHandler(click -> grid.removeColumn(column));
         * column.setCellRenderer(new PLabelCellRenderer<>(from -> (int)
         * (Math.random() * 1000) + "")); column.setHeaderRenderer(() ->
         * anchor); grid.addColumnDescriptor(column); },
         * Duration.ofMillis(2000));
         **/
    }

    private void createNewEvent() {
        final EventHandler<PClickEvent> handler = UIContext.getNewEventBus().subscribe(PClickEvent.class,
                event -> System.err.println("B " + event));
        UIContext.getNewEventBus().post(new PClickEvent(this));
        UIContext.getNewEventBus().post(new PClickEvent(this));
        UIContext.getNewEventBus().unsubscribe(handler);
        UIContext.getNewEventBus().post(new PClickEvent(this));
    }

    private static final class Data {

        protected Integer key;
        protected String value;

        public Data(final Integer key, final String value) {
            this.key = key;
            this.value = value;
        }
    }

    private RefreshableDataGrid<Integer, Data> createGrid() {
        final AbstractGridWidget listView = new GridTableWidget();
        listView.setStyleProperty("table-layout", "fixed");
        final RefreshableDataGrid<Integer, Data> grid = new RefreshableDataGrid<>(listView);

        final DataGridColumnDescriptor<Data, Data> columnDescriptor1 = new DataGridColumnDescriptor<>();
        columnDescriptor1.setCellRenderer(new CellRenderer<UISampleEntryPoint.Data, PLabel>() {

            @Override
            public void update(final Data value, final Cell<Data, PLabel> current) {
                current.getWidget().setText(value.key + "");
            }

            @Override
            public PLabel render(final int row, final Data value) {
                return Element.newPLabel(value.key + "");
            }
        });
        columnDescriptor1.setHeaderCellRenderer(() -> Element.newPLabel("A"));
        columnDescriptor1.setValueProvider(new IdentityValueProvider<>());
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);
        grid.addDataGridColumnDescriptor(columnDescriptor1);

        for (int i = 0; i < 40; i++) {

            final DataGridColumnDescriptor<Data, Data> columnDescriptor3 = new DataGridColumnDescriptor<>();
            columnDescriptor3.setCellRenderer(new CellRenderer<UISampleEntryPoint.Data, PLabel>() {

                @Override
                public void update(final Data value, final Cell<Data, PLabel> current) {
                    current.getWidget().setText(value.value);
                }

                @Override
                public PLabel render(final int row, final Data value) {
                    return Element.newPLabel(value.value);
                }
            });
            columnDescriptor3.setHeaderCellRenderer(() -> Element.newPLabel("B"));
            columnDescriptor3.setValueProvider(new IdentityValueProvider<>());
            grid.addDataGridColumnDescriptor(columnDescriptor3);
        }

        grid.setData(0, 1, new Data(1, "AA"));
        grid.setData(1, 2, new Data(2, "BB"));
        final Data data = new Data(3, "CC");
        grid.setData(2, 3, data);

        final AtomicInteger i = new AtomicInteger();
        PScheduler.scheduleWithFixedDelay(() -> {
            for (int key = 1; key < 50; key++) {
                grid.setData(key - 1, key, new Data(key, "" + i.incrementAndGet()));
            }
        }, Duration.ofSeconds(1), Duration.ofMillis(100));

        return grid;
    }

    private void testUIDelegator() {
        final PLabel a = Element.newPLabel();
        PWindow.getMain().add(a);
        final AtomicInteger ai = new AtomicInteger();
        PScheduler.scheduleAtFixedRate(() -> a.setText("a " + ai.incrementAndGet()), Duration.ofMillis(0), Duration.ofMillis(10));

        final PLabel p = Element.newPLabel();
        PWindow.getMain().add(p);
    }

    private PWindow createWindow() {
        final PWindow w = Element.newPWindow("Window 1", "resizable=yes,location=0,status=0,scrollbars=0");

        // PScript.execute(w, "alert('coucou Window1');");
        PScript.execute(w, "console.log('coucou Window1');");

        final PFlowPanel windowContainer = Element.newPFlowPanel();
        w.add(windowContainer);

        final PLabel child = Element.newPLabel("Window 1");
        child.setText("Modified Window 1");
        windowContainer.add(child);

        final PButton button = Element.newPButton("Modified main label on main window");
        windowContainer.add(button);
        button.addClickHandler(event -> {
            mainLabel.setText("Touched by God : " + child.getWindow());
            PScript.execute(PWindow.getMain(), "alert('coucou');");
            child.setText("Clicked Window 1");
        });
        windowContainer.add(button);

        final AtomicInteger i = new AtomicInteger();

        final PButton button1 = Element.newPButton("Open linked window");
        windowContainer.add(button1);
        button1.addClickHandler(event -> {
            final PWindow newPWindow = Element.newPWindow(w, "Sub Window 1 " + i.incrementAndGet(),
                    "resizable=yes,location=0,status=0,scrollbars=0");
            newPWindow.add(Element.newPLabel("Sub window"));
            newPWindow.open();
        });

        final PButton button2 = Element.newPButton("Open not linked window");
        windowContainer.add(button2);
        button2.addClickHandler(event -> {
            final PWindow newPWindow = Element.newPWindow("Not Sub Window 1 " + i.incrementAndGet(),
                    "resizable=yes,location=0,status=0,scrollbars=0");
            newPWindow.add(Element.newPLabel("Sub window"));
            newPWindow.open();
        });

        PScheduler.scheduleAtFixedRate(() -> {
            final PLabel label = Element.newPLabel();
            label.setText("Window 1 " + i.incrementAndGet());
            windowContainer.add(label);
            windowContainer.add(Element.newPCheckBox("Checkbox"));
        }, Duration.ofSeconds(1), Duration.ofSeconds(10));

        final PFrame frame = Element.newPFrame("http://localhost:8081/sample/");
        frame.add(Element.newPLabel("Inside the frame"));
        w.add(frame);

        return w;
    }

    @Override
    public void onUserLoggedOut(final UserLoggedOutEvent event) {
        UIContext.get().close();
    }

    private static final PStackLayoutPanel createStackLayoutPanel() {
        final PStackLayoutPanel child = Element.newPStackLayoutPanel(PUnit.CM);
        child.add(Element.newPLabel("Text"), "Text", false, 1.0);
        return child;
    }

    private static final PListBox createListBox() {
        final PListBox pListBox = Element.newPListBox(true);
        pListBox.addItem("A");
        pListBox.addItem("B");
        pListBox.insertItem("C", 1);
        pListBox.addItemsInGroup("sport", "Baseball", "Basketball", "Football", "Hockey", "Water Polo");
        return pListBox;
    }

    private static final PTabLayoutPanel createTabLayoutPanel() {
        final PTabLayoutPanel child = Element.newPTabLayoutPanel();
        child.add(Element.newPLabel("text"), "text");
        return child;
    }

    private static final PMenuBar createMenu() {
        final PMenuBar pMenuBar = Element.newPMenuBar(true);
        pMenuBar.addItem(Element.newPMenuItem("Menu 1", Element.newPMenuBar()));
        pMenuBar.addItem(Element.newPMenuItem("Menu 2", true, Element.newPMenuBar()));
        pMenuBar.addItem(Element.newPMenuItem("Menu 3", () -> System.err.println("Menu click")));
        pMenuBar.addSeparator();
        return pMenuBar;
    }

    private static final PFlowPanel createPFlowPanel() {
        final PFlowPanel panel1 = Element.newPFlowPanel();
        panel1.setAttribute("id", "panel1");
        final PFlowPanel panel2_1 = Element.newPFlowPanel();
        panel2_1.setAttribute("id", "panel2_1");
        final PFlowPanel panel3_1_1 = Element.newPFlowPanel();
        panel3_1_1.setAttribute("id", "panel3_1_1");
        final PFlowPanel panel3_1_2 = Element.newPFlowPanel();
        panel3_1_2.setAttribute("id", "panel3_1_2");
        final PFlowPanel panel2_2 = Element.newPFlowPanel();
        panel2_2.setAttribute("id", "panel2_2");
        final PFlowPanel panel3_2_1 = Element.newPFlowPanel();
        panel3_2_1.setAttribute("id", "panel3_2_1");
        final PFlowPanel panel3_2_2 = Element.newPFlowPanel();
        panel3_2_2.setAttribute("id", "panel3_2_2");

        panel1.add(panel2_1);
        panel2_1.add(panel3_1_1);
        panel2_1.add(panel3_1_2);
        panel1.add(panel2_2);
        panel2_2.add(panel3_2_1);
        panel2_2.add(panel3_2_2);

        return panel1;
    }

    private static final PWidget createBlock(final PWidget child) {
        final PFlowPanel panel = Element.newPFlowPanel();
        panel.add(child);
        return panel;
    }

    private static final PDockLayoutPanel createDockLayoutPanel() {
        final PDockLayoutPanel pDockLayoutPanel = Element.newPDockLayoutPanel(PUnit.CM);
        pDockLayoutPanel.addNorth(Element.newPLabel("LabelDock"), 1.5);
        return pDockLayoutPanel;
    }

    private static final PFlowPanel createDateBox() {
        final PFlowPanel flowPanel = Element.newPFlowPanel();
        final PDateBox dateBox = Element.newPDateBox();
        dateBox.setValue(new Date(0));
        flowPanel.add(dateBox);
        final PButton button = Element.newPButton("reset");
        button.addClickHandler(event -> dateBox.setValue(null));
        flowPanel.add(button);
        return flowPanel;
    }

    private static final LoggerAddOn createPAddOn() {
        final LoggerAddOn labelPAddOn = new LoggerAddOn();
        labelPAddOn.log("addon logger test");

        labelPAddOn.setAjaxHandler((req, resp) -> {
            final String header = req.getHeader("info");

            if (header.equals("Get Data")) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.getWriter().print("{\"response\": \"" + header + "\"}");
                resp.getWriter().flush();
            } else {
                resp.sendError(500);
            }
        });

        labelPAddOn.setTerminalHandler(event -> System.err.println(event.toString()));

        return labelPAddOn;
    }

    private static final PTextBox createPTextBox() {
        final PTextBox pTextBox = Element.newPTextBox();

        pTextBox.addKeyUpHandler(new PKeyUpHandler() {

            @Override
            public void onKeyUp(final PKeyUpEvent keyUpEvent) {
                PScript.execute(PWindow.getMain(), "alert('" + keyUpEvent + "');");
            }

            @Override
            public PKeyCodes[] getFilteredKeys() {
                return new PKeyCodes[]{PKeyCodes.ENTER};
            }
        });
        return pTextBox;
    }

    private static final PTree createTree() {
        final PTree tree = Element.newPTree();

        final PTreeItem firstFolder = tree.add("First");
        firstFolder.add("2");
        firstFolder.add(0, Element.newPTreeItem("1"));

        firstFolder.setState(true);

        final PTreeItem secondFolder = Element.newPTreeItem("Second");
        final PTreeItem subItem = secondFolder.add(Element.newPTreeItem());
        subItem.setText("3");
        secondFolder.add(Element.newPTreeItem(Element.newPLabel("4")));
        tree.add(secondFolder);

        secondFolder.setSelected(true);

        return tree;
    }

    private static final PAbsolutePanel createAbsolutePanel() {
        final PAbsolutePanel pAbsolutePanel = Element.newPAbsolutePanel();
        pAbsolutePanel.add(Element.newDiv());
        pAbsolutePanel.add(Element.newP());
        return pAbsolutePanel;
    }

    private static final PButton createButton() {
        final PButton pButton = Element.newPButton("Button 1");
        pButton.addClickHandler(handler -> pButton.setText("Button 1 clicked"));
        return pButton;
    }

    class Pojo {

        public String security;
        public String classe;
        public Double bid;
        public Double offer;
        public Double spread;
        public String coucou;
        public String coucou1;
        public String coucou2;
        public String coucou3;
        public String coucou4;
        public String coucou5;
        public String coucou6;
        public String coucou7;
        public String coucou8;
        public String coucou9;
        public String coucou10;

        /**
         * @return the security
         */
        public String getSecurity() {
            return security;
        }

        /**
         * @param security the security to set
         */
        public void setSecurity(final String security) {
            this.security = security;
        }

        /**
         * @return the classe
         */
        public String getClasse() {
            return classe;
        }

        /**
         * @param classe the classe to set
         */
        public void setClasse(final String classe) {
            this.classe = classe;
        }

        /**
         * @return the bid
         */
        public Double getBid() {
            return bid;
        }

        /**
         * @param bid the bid to set
         */
        public void setBid(final Double bid) {
            this.bid = bid;
        }

        /**
         * @return the offer
         */
        public Double getOffer() {
            return offer;
        }

        /**
         * @param offer the offer to set
         */
        public void setOffer(final Double offer) {
            this.offer = offer;
        }

        /**
         * @return the spread
         */
        public Double getSpread() {
            return spread;
        }

        /**
         * @param spread the spread to set
         */
        public void setSpread(final Double spread) {
            this.spread = spread;
        }

        /**
         * @return the coucou
         */
        public String getCoucou() {
            return coucou;
        }

        /**
         * @param coucou the coucou to set
         */
        public void setCoucou(final String coucou) {
            this.coucou = coucou;
        }

        /**
         * @return the coucou1
         */
        public String getCoucou1() {
            return coucou1;
        }

        /**
         * @param coucou1 the coucou1 to set
         */
        public void setCoucou1(final String coucou1) {
            this.coucou1 = coucou1;
        }

        /**
         * @return the coucou2
         */
        public String getCoucou2() {
            return coucou2;
        }

        /**
         * @param coucou2 the coucou2 to set
         */
        public void setCoucou2(final String coucou2) {
            this.coucou2 = coucou2;
        }

        /**
         * @return the coucou3
         */
        public String getCoucou3() {
            return coucou3;
        }

        /**
         * @param coucou3 the coucou3 to set
         */
        public void setCoucou3(final String coucou3) {
            this.coucou3 = coucou3;
        }

        /**
         * @return the coucou4
         */
        public String getCoucou4() {
            return coucou4;
        }

        /**
         * @param coucou4 the coucou4 to set
         */
        public void setCoucou4(final String coucou4) {
            this.coucou4 = coucou4;
        }

    }

}
