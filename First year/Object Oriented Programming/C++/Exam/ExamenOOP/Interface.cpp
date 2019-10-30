#include "Interface.h"
#include <qmessagebox.h>
void Interface::initGUI()
{
	QVBoxLayout* vboxLayout = new QVBoxLayout;
	setLayout(vboxLayout);

	tableView = new QTableView;
	tableView->setModel(tableModel);
	vboxLayout->addWidget(tableView);

	QWidget* dataWidget = new QWidget;
	QFormLayout* dataLayout = new QFormLayout;
	dataWidget->setLayout(dataLayout);
	dataLayout->addRow(idLabel, idLine);
	dataLayout->addRow(nameLabel, nameLine);
	dataLayout->addRow(typeLabel, typeLine);
	dataLayout->addRow(priceLabel, priceLine);
	dataLayout->addRow(priceSliderLabel, priceSlider);
	vboxLayout->addWidget(dataWidget);

	QWidget* buttonWidget = new QWidget;
	QHBoxLayout* hboxLayout = new QHBoxLayout;
	buttonWidget->setLayout(hboxLayout);

	hboxLayout->addWidget(addButton);

	vboxLayout->addWidget(buttonWidget);
	
}

void Interface::createWindows() {
	vector<string> types;
	int flag = 0;
	for (auto p : service.getAll()) {
		flag = 0;
		for (auto s : types) {
			if (s == p.getType()) {
				flag = 1;
			}
		}
		if (flag == 0) {
			types.push_back(p.getType());
		}
	}

	for (auto p : types) {
		Window* w = new Window(service, p);
		windows.push_back(w);
		w->setMinimumWidth(300);
		w->setWindowTitle(QString::fromStdString(p));
		w->show();
		addObs(w);
	}
}

void Interface::initSignals()
{
	QObject::connect(tableView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &Interface::onSelectionChanged);
	QObject::connect(addButton, &QPushButton::clicked, this, &Interface::addFunction);
	QObject::connect(priceSlider, &QSlider::valueChanged, this, &Interface::filterPrice);
	priceSlider->setMaximum(50);
	priceSlider->setMinimum(0);
}

void Interface::setState()
{
	tableModel->setData(service.sortProducts());
	
}

void Interface::onSelectionChanged()
{
	auto sel = tableView->selectionModel()->selectedIndexes();
	if (sel.isEmpty()) {
		idLine->setText("");
		nameLine->setText("");
		typeLine->setText("");
		priceLine->setText("");
		priceSlider->setValue(0);
		return;
	}
	auto firstSel = sel.at(0);
	Produs p = tableModel->getData(firstSel);
	idLine->setText(QString::fromStdString(to_string(p.getId())));
	nameLine->setText(QString::fromStdString(p.getName()));
	typeLine->setText(QString::fromStdString(p.getType()));
	priceLine->setText(QString::fromStdString(to_string(p.getPrice())));
}

void Interface::addFunction()
{
	string id = idLine->text().toStdString();
	string type = typeLine->text().toStdString();
	string name = nameLine->text().toStdString();
	string price = priceLine->text().toStdString();
	try {
		service.addProdus(stoi(id), name, type, stod(price));
	}
	catch (exception& msg) {
		QMessageBox error;
		error.setText(QString::fromStdString(msg.what()));
		error.exec();
	}
	setState();
	notifyObsss();
}

void Interface::filterPrice()
{
	vector<Produs> vector = service.getAll();
	int total = 0;
	for (int i = 0; i < vector.size(); i++) {
		if (vector[i].getPrice() <= priceSlider->value()) {
			total++;
		}
	}
	priceSliderLabel->setText(QString::fromStdString(to_string(total)));
}

Interface::~Interface()
{
}

void Window::initGUI()
{
	QVBoxLayout* vboxLayout = new QVBoxLayout;
	setLayout(vboxLayout);

	vboxLayout->addWidget(typeLabel);
}

void Window::setState()
{
	int total = 0;
	for (auto product : service.getAll()) {
		if (product.getType() == this->type) {
			total++;
		}
	}
	typeLabel->setText(QString::fromStdString(to_string(total)));
}

void Window::update()
{
	setState();
}
