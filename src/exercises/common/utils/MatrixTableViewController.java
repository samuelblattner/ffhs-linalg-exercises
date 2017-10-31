package exercises.common.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by samuelblattner on 29.10.17.
 */
public class MatrixTableViewController {

    private TableView tableView;
    private Matrix matrix;

    private ObservableList<MatrixRow> createObservableRowList() {
        ObservableList<MatrixRow> rows = FXCollections.observableArrayList();

        for (int r = 0; r < this.matrix.getNumRows(); r++) {

            MatrixRow matrixRow = new MatrixRow(this.matrix.getNumCols());

            for (int c = 0; c < this.matrix.getNumCols(); c++) {
                matrixRow.setColumnValue(c, new SimpleStringProperty(this.matrix.getStringValue(c, r)));
            }
            rows.add(matrixRow);
        }

        return rows;
    }

    private void createTableColumns() {

        ObservableList cols = this.tableView.getColumns();

        for (int c = 0; c < this.matrix.getNumCols(); c++) {

                TableColumn col;

                if (cols.size() > c) {
                    col = (TableColumn) cols.get(c);
                } else {
                    col = new TableColumn(String.format("%d", c));
                    this.tableView.getColumns().add(col);
                }

                final int c2 = c ;
                col.setEditable(true);
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setMinWidth(100);
                col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent event) {
                        MatrixRow row = (MatrixRow) event.getTableView().getItems().get(
                                event.getTablePosition().getRow());

                        matrix.setValue(c2, event.getTablePosition().getRow(), Double.valueOf((String)event.getNewValue()));

                    }
                });

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                    @Override
                    public ObservableValue call(TableColumn.CellDataFeatures param) {

                        MatrixRow obs = (MatrixRow) param.getValue();
                        return obs.getColumnValue(c2);
                    }
                });


        }
    }

    public MatrixTableViewController(TableView tableView, Matrix matrix) {
        this.matrix = matrix;
        this.tableView = tableView;

        this.createTableColumns();
        this.tableView.setItems(this.createObservableRowList());
    }

    public void setMatrix(Matrix newMatrix) {
        this.matrix = newMatrix;
        this.createTableColumns();
        this.tableView.setItems(this.createObservableRowList());
    }

    public void refreshTable() {
        this.tableView.refresh();
    }


    private class MatrixRow {

        private ArrayList<ObservableValue<String>> colValues = new ArrayList<ObservableValue<String>>();

        public MatrixRow(int numCols) {
            for (int c = 0; c < numCols; c++) {
                this.colValues.add(new SimpleStringProperty());
            }
        }

        public void setColumnValue(int col, ObservableValue value) {
            this.colValues.set(col, value);
        }

        public ObservableValue getColumnValue(int col) {

//            return new SimpleDoubleProperty(123.2);
            return this.colValues.get(col);
        }
    }

}
